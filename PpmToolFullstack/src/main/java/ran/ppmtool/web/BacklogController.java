package ran.ppmtool.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ran.ppmtool.domain.ProjectTask;
import ran.ppmtool.services.MapValidationErrorService;
import ran.ppmtool.services.ProjectTaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                          @PathVariable String backlog_id)
    {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findByBacklogById(backlog_id);
    }
}
