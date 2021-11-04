package com.bandtec.mais.consulta.models.dto.request;

import com.bandtec.mais.consulta.domain.Agendamento;
import com.bandtec.mais.consulta.domain.Consulta;
import com.bandtec.mais.consulta.domain.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class AgendamentoConsultaRequestDTO {
    LocalDate dtAtendimento;
    LocalTime hrAtendimento;
    String descricao;
    Integer idPaciente;
    Integer idMedico;
    Integer idUbs;

    public static Consulta convertFromController(AgendamentoConsultaRequestDTO agendamentoConsultaRequestDTO) {
        log.info("Consulta DTO {}", agendamentoConsultaRequestDTO);

        Agendamento agendamento = Agendamento
                .builder()
                .dhInsert(LocalDateTime.now())
                .dtAtendimento(agendamentoConsultaRequestDTO.dtAtendimento)
                .hrAtendimento(agendamentoConsultaRequestDTO.hrAtendimento)
                .build();

        return Consulta
                .builder()
                .descricao(agendamentoConsultaRequestDTO.descricao)
                .agendamento(agendamento)
                .build();
    }
}
