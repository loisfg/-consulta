package com.bandtec.mais.consulta.gateway.controller;

import com.bandtec.mais.consulta.domain.Consulta;
import com.bandtec.mais.consulta.domain.Exame;
import com.bandtec.mais.consulta.models.dto.request.AgendamentoConsultaRequestDTO;
import com.bandtec.mais.consulta.models.dto.request.AgendamentoExameRequestDTO;
import com.bandtec.mais.consulta.models.dto.response.EspecialidadeResponseDTO;
import com.bandtec.mais.consulta.usecase.schedule.*;
import com.bandtec.mais.consulta.usecase.ubs.PostHoursUbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@CrossOrigin("*")
@RestController
@RequestMapping("agendamento")
public class AgendamentoController {

    @Autowired
    private PostAgendamentoConsulta postAgendamentoConsulta;

    @Autowired
    private PostAgendamentoExame postAgendamentoExame;

    @Autowired
    private GetAgendamentoExame getAgendamentoExame;

    @Autowired
    private GetAgendamentoConsulta getAgendamentoConsulta;

    @Autowired
    private CancelAgendamento cancelAgendamento;

    @Autowired
    private PostHoursUbs postHoursUbs;

    @Autowired
    private GetEspecialidades getEspecialidades;

    @PatchMapping("/cancelar/{idAgendamento}")
    public ResponseEntity<?> cancelarExame(@PathVariable Integer idAgendamento) {
        return ResponseEntity.ok(cancelAgendamento.execute(idAgendamento));
    }

    @PostMapping("/agendar/exame")
    public ResponseEntity<Exame> createAgendamentoExame(
            @RequestBody AgendamentoExameRequestDTO agendamentoExameRequestDTO
    ) {
        return postAgendamentoExame.execute(agendamentoExameRequestDTO)
                .map(it -> ResponseEntity.status(HttpStatus.CREATED).body(it))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PostMapping("/agendar/consulta")
    public ResponseEntity<Consulta> createAgendamentoConsulta(
            @RequestBody @Valid AgendamentoConsultaRequestDTO agendamentoConsultaRequestDTO
    ) {
        return postAgendamentoConsulta.execute(agendamentoConsultaRequestDTO)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseGet(ResponseEntity.status(HttpStatus.BAD_REQUEST)::build);
    }

    @GetMapping("/exames/{idUser}")
    public ResponseEntity<?> getExamesByIdUser(@PathVariable Integer idUser) {
        return getAgendamentoExame.execute(idUser)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseGet(ResponseEntity.status(HttpStatus.NO_CONTENT)::build);
    }

    @GetMapping("/consulta/{idUser}")
    public ResponseEntity<?> getConsultaByIdUser(@PathVariable Integer idUser) {
        return getAgendamentoConsulta.execute(idUser)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseGet(ResponseEntity.status(HttpStatus.NO_CONTENT)::build);
    }

    @GetMapping("/horarios/livres/{dia}/{idUbs}")
    public ResponseEntity<HashMap<LocalTime, String>> getAvaibleTime(@PathVariable Integer idUbs,
                                                                     @PathVariable String dia) {
        HashMap<LocalTime, String> listHoras = postHoursUbs.execute(idUbs, dia);
        if(listHoras.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(listHoras);
    }

    @GetMapping("/especialidades")
    public ResponseEntity<Set<EspecialidadeResponseDTO>> getEspecialidades() {
        return ResponseEntity.of(getEspecialidades.execute());

    }
}
