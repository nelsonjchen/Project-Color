package com.mindflakes.colorizedproject.listeners

import com.mindflakes.colorizedproject.color_setters.ColorSetterFactory
import com.mindflakes.colorizedproject.gColorLockedComponentMap
import com.mindflakes.colorizedproject.gProjectColorLockedMap
import com.mindflakes.colorizedproject.gProjectColorMap
import com.mindflakes.colorizedproject.gProjectLockedComponentsMap
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.mindflakes.colorizedproject.services.ProjectService

internal class ProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<ProjectService>()
    }

    override fun projectClosed(project: Project) {
        val currentColorSetter = ColorSetterFactory.getColorSetter()
        try {
            gProjectColorMap.remove(project)
            gProjectColorLockedMap.remove(project)
            for(component in gProjectLockedComponentsMap[project]!!){
                gColorLockedComponentMap.remove(component)
            }
            gProjectLockedComponentsMap.remove(project)
            currentColorSetter.cleanUp(project)
        } catch (_: Exception) {
        }
    }
}