package ai.arcblroth.overcaffeinated.services

import ai.arcblroth.overcaffeinated.OCResourceBundle
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vcs.VcsNotifier
import git4idea.GitUtil
import git4idea.actions.GitInit
import git4idea.commands.Git
import git4idea.i18n.GitBundle

class OCProjectService(project: Project) {

    val logger = Logger.getInstance(OCProjectService::class.java.name)
    var isOvercaffeinatedRunning = false
        private set
    var isGitInit = false
        private set

    init {
        logger.info(OCResourceBundle.message("projectService", project.name))
        Notifications.Bus.notify(
            Notification(
                "ai.arcblroth.overcaffeinated",
                OCResourceBundle.message("name"),
                OCResourceBundle.message("projectServiceInitMessage"),
                NotificationType.INFORMATION
            )
        )

        isOvercaffeinatedRunning = true

        // Init Git
        val root = project.guessProjectDir()
        println(root?.toString())
        if (root != null) {
            if (!GitUtil.isUnderGit(root)) {
                logger.info("Running git init")
                object : Backgroundable(project, GitBundle.getString("common.refreshing")) {
                    override fun run(indicator: ProgressIndicator) {
                        val result = Git.getInstance().init(project, root)
                        if (!result.success()) {
                            VcsNotifier.getInstance(project).notifyError(
                                OCResourceBundle.message("gitInitFailed"),
                                result.errorOutputAsHtmlString
                            )
                            return
                        }
                        if (project.isDefault) {
                            return
                        }
                        GitInit.refreshAndConfigureVcsMappings(project, root, root.path)
                    }
                }.queue()
            } else {
                logger.info("Not running git init")
            }
            isGitInit = true
        }
    }

    companion object {
        fun getInstance(project: Project): OCProjectService? {
            return project.getService(OCProjectService::class.java)
        }
    }
}
