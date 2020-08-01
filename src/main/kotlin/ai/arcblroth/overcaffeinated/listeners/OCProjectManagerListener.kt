package ai.arcblroth.overcaffeinated.listeners

import ai.arcblroth.overcaffeinated.services.OCProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class OCProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        // Creates service, contrary to name
        project.getService(OCProjectService::class.java)
    }

}
