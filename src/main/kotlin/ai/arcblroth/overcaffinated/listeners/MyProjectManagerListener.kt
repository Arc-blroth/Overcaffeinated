package ai.arcblroth.overcaffinated.listeners

import ai.arcblroth.overcaffinated.services.OCProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.getService(OCProjectService::class.java)
    }
}
