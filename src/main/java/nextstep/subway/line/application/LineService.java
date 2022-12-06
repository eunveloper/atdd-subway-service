package nextstep.subway.line.application;

import nextstep.subway.exception.EntityNotFoundException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.line.dto.SectionRequest;
import nextstep.subway.line.repository.LineRepository;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineService {

    private LineRepository lineRepository;
    private StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        Line persistLine = lineRepository.save(
                new Line(request.getName(),
                        request.getColor(),
                        upStation,
                        downStation,
                        request.getDistance(),
                        request.getAddedPrice()));

        return LineResponse.of(persistLine);
    }

    public List<LineResponse> findLines() {
        List<Line> persistLines = lineRepository.findAll();
        return persistLines
                .stream()
                .map(LineResponse::of)
                .collect(Collectors.toList());
    }

    public Line findLineById(Long id) {
        return lineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Line", id));
    }


    public LineResponse findLineResponseById(Long id) {
        Line persistLine = findLineById(id);
        return LineResponse.of(persistLine);
    }

    @Transactional
    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        Line persistLine = findLineById(id);
        persistLine.update(new Line(lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    @Transactional
    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    @Transactional
    public void addLineStation(Long lineId, SectionRequest request) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        line.addStation(upStation, downStation, request.getDistance());
    }

    @Transactional
    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findById(stationId);
        line.removeStation(station);
    }

}
