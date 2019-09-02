package ran.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ran.ppmtool.domain.Backlog;
import ran.ppmtool.domain.ProjectTask;
import ran.ppmtool.repository.BacklogRepository;
import ran.ppmtool.repository.ProjectTaskRepository;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //Exception: No project
        //PTs to be a specific project, project != null, Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //set the backlog to the project task
        projectTask.setBacklog(backlog);
        //project sequence = IDPRO-1 => IDPRO-2
        Integer BacklogSequence = backlog.getPTSequence();
        //Update the Backlog sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        //Add sequence to project task
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //Initial priority when priority null.
        if(projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }
        // Initial status when status is null.
        if(projectTask.getStatus() == "" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable <ProjectTask> findByBacklogById(String id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
