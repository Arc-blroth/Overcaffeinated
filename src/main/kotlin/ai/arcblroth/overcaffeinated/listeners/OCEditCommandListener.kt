package ai.arcblroth.overcaffeinated.listeners

import com.intellij.ide.SaveAndSyncHandler
import com.intellij.openapi.command.CommandEvent
import com.intellij.openapi.command.CommandListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class OCEditCommandListener : CommandListener {

    override fun commandFinished(event: CommandEvent) {
        if(event.project != null) {
            // Force save the project
            FileDocumentManager.getInstance().saveAllDocuments()
            (SaveAndSyncHandler.getInstance()).scheduleSave(
                    SaveAndSyncHandler.SaveTask(
                        onlyProject = event.project,
                        forceSavingAllSettings = true,
                        saveDocuments = false
                    ), forceExecuteImmediately = true)
        }
    }

}
