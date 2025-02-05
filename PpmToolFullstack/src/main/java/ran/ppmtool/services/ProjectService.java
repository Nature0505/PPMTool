package ran.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ran.ppmtool.domain.Backlog;
import ran.ppmtool.domain.Project;
import ran.ppmtool.exceptions.ProjectIdException;
import ran.ppmtool.repository.BacklogRepository;
import ran.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){

        //Will be a LOT of LOGIC here
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID doesn't exist ");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("The project with ID '"+projectId+"' doesn't exist.");
        }
        projectRepository.delete(project);
    }

//    public Project updateProjectByIdentifier(String projectId){
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//        if(project == null){
//            throw new ProjectIdException("Project ID doesn't exist ");
//        }
//    }
}
