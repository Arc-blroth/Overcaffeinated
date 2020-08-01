package ai.arcblroth.overcaffeinated

import ai.arcblroth.overcaffeinated.services.OCProjectService
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.jetbrains.rd.util.getOrCreate
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

private val locks = WeakHashMap<Project, AtomicBoolean>()

internal fun syncProject(project: Project, timeout: Long = 0) {
    if(!project.isDefault) {
        val lock = locks.getOrCreate(project) { AtomicBoolean(false) }
        OCProjectService.getInstance(project)?.logger?.debug("Syncing project")
        if(!lock.get()) {
            lock.set(true)
            object : Task.Backgroundable(project, OCResourceBundle.message("syncing")) {
                override fun run(indicator: ProgressIndicator) {
                    try {
                        Thread.sleep(timeout)
                        val root = project.guessProjectDir()!!
                        val addCommand = GitLineHandler(project, root, GitCommand.ADD)
                        addCommand.addParameters(".")
                        Git.getInstance().runCommand(addCommand)
                        val commitCommand = GitLineHandler(project, root, GitCommand.COMMIT)
                        commitCommand.addParameters(
                            "-m",
                            OCResourceBundle.message("name") + " " + System.currentTimeMillis().toString()
                        )
                        Git.getInstance().runCommand(commitCommand)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFinished() {
                    lock.set(false)
                }
            }.queue()
        }
    }
}