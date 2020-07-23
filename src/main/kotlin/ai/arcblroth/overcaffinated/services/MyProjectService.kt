package ai.arcblroth.overcaffinated.services

import ai.arcblroth.overcaffinated.MyBundle
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
        Notifications.Bus.notify(
            Notification(
                "ai.arcblroth.overcaffinated",
                "Stuff",
                "is happening",
                NotificationType.INFORMATION
            )
        )
    }
}
