package io.github.Thiago279.JavaDIO.service.impl;

import io.github.Thiago279.JavaDIO.dto.PartidaRequestDTO;
import io.github.Thiago279.JavaDIO.model.Partida;
import io.github.Thiago279.JavaDIO.model.Time;
import io.github.Thiago279.JavaDIO.repository.PartidaRepository;
import io.github.Thiago279.JavaDIO.repository.TimeRepository;
import io.github.Thiago279.JavaDIO.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidaServiceImpl implements PartidaService {

    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    private TimeRepository timeRepository;
    @Override
    public void atualizarTabela(Partida partida) {
        Time timeCasa = partida.getTimeCasa();
        Time timeVisitante = partida.getTimeVisitante();

        atualizaGolsTime(partida , partida.getTimeCasa());
        atualizaGolsTime(partida , partida.getTimeVisitante());

        atribuiPontos(partida, timeCasa, timeVisitante);

        timeRepository.save(partida.getTimeCasa());
        timeRepository.save(partida.getTimeVisitante());

    }


    @Override
    public void atualizaGolsTime(Partida partida, Time time){
        int golsCasa = partida.getGolsCasa();
        int golsVisitante = partida.getGolsVisitante();

        if(time.isMandante(partida)){
            time.setGolsMarcados(time.getGolsMarcados() + golsCasa);
            time.setGolsSofridos(time.getGolsSofridos() + golsVisitante);
        }
        else{
            time.setGolsMarcados(time.getGolsMarcados() + golsVisitante);
            time.setGolsSofridos(time.getGolsSofridos() + golsCasa);
        }
    }

    @Override
    public void atribuiPontos(Partida partida, Time timeCasa, Time timeVisitante){
        int golsCasa = partida.getGolsCasa();
        int golsVisitante = partida.getGolsVisitante();

        if (golsCasa > golsVisitante){
            timeCasa.setPontos(timeCasa.getPontos() + 3);
        } else if (golsVisitante > golsCasa) {
            timeVisitante.setPontos(timeVisitante.getPontos() + 3);
        }
        else{
            timeCasa.setPontos(timeCasa.getPontos() + 1);
            timeVisitante.setPontos(timeVisitante.getPontos() + 1);
        }
    }
    @Override
    public List<Partida> findAll() {

        return partidaRepository.findAll();
    }

    @Override
    public void savePartida(PartidaRequestDTO partidaRequest) {
        // Recupera os times a partir do banco de dados, utilizando os nomes passados
        Time timeCasa = timeRepository.findByNome(partidaRequest.getTimeCasa())
                .orElseThrow(() -> new RuntimeException("Time da casa não encontrado"));

        Time timeVisitante = timeRepository.findByNome(partidaRequest.getTimeVisitante())
                .orElseThrow(() -> new RuntimeException("Time visitante não encontrado"));

        // Cria e configura a nova partida
        Partida partida = new Partida(timeCasa, timeVisitante,
                partidaRequest.getGolsCasa(), partidaRequest.getGolsVisitante());

        atualizarTabela(partida);
        partidaRepository.save(partida);
    }
}
