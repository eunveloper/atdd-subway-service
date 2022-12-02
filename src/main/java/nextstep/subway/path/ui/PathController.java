package nextstep.subway.path.ui;

import nextstep.subway.path.application.PathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping("/paths")
    public ResponseEntity findPaths(@RequestParam Long source,
                                     @RequestParam Long target) {
        return ResponseEntity.ok().body(pathService.findPaths(source, target));
    }

}
