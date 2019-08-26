package ran.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ran.ppmtool.domain.Project;
import ran.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    public Project saveOrUpdateProject(Project project){

        //Will be a LOT of LOGIC here

        return projectRepository.save(project);
    }
}
