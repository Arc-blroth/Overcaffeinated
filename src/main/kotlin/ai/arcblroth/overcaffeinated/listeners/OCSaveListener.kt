package ai.arcblroth.overcaffeinated.listeners

import ai.arcblroth.overcaffeinated.syncProject
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent

class OCSaveListener : BulkFileListener {

    override fun after(events: MutableList<out VFileEvent>) {
        // When things are written to disk, invoke git sync
        ProjectManager.getInstance().openProjects.forEach { syncProject(it) }
    }
}
