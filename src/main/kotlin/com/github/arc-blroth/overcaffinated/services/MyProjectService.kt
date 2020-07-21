package com.github.arc-blroth.overcaffinated.services

import com.intellij.openapi.project.Project
import com.github.arc-blroth.overcaffinated.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
