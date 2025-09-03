package org.pet.project.rickandmorty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform