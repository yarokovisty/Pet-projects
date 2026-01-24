package org.pet.project.rickandmorty.feature.character.impl.data.datasource

object TestJsonResources {

    val characterListSingleCharacterOnePage = """
        {
            "info": {
                "count": 1,
                "pages": 1,
                "next": "https://rickandmortyapi.com/api/character?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Character 1",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/1.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                }
            ]
        }
    """.trimIndent()

    val characterListTwoCharactersOnePage = """
        {
            "info": {
                "count": 2,
                "pages": 1,
                "next": "https://rickandmortyapi.com/api/character?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Character 1",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/1.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                },
                {
                    "id": 2,
                    "name": "Character 2",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/2.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/2",
                    "created": "2017-11-04T18:48:46.250Z"
                }
            ]
        }
    """.trimIndent()

    val characterListTwoPages = """
        {
            "info": {
                "count": 20,
                "pages": 2,
                "next": "https://rickandmortyapi.com/api/character?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Character 1",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/1.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                }
            ]
        }
    """.trimIndent()

    val characterListEmpty = """
        {
            "info": {
                "count": 0,
                "pages": 0,
                "next": null,
                "prev": null
            },
            "results": []
        }
    """.trimIndent()

    val characterListSinglePageNoNext = """
        {
            "info": {
                "count": 1,
                "pages": 1,
                "next": null,
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Character 1",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/1.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                }
            ]
        }
    """.trimIndent()

    val characterListTwoCharactersNoNext = """
        {
            "info": {
                "count": 2,
                "pages": 1,
                "next": null,
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Character 1",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/1.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/1",
                    "created": "2017-11-04T18:48:46.250Z"
                },
                {
                    "id": 2,
                    "name": "Character 2",
                    "status": "Alive",
                    "species": "Human",
                    "type": "",
                    "gender": "Male",
                    "origin": {"name": "Earth", "url": ""},
                    "location": {"name": "Earth", "url": ""},
                    "image": "https://example.com/2.jpeg",
                    "episode": ["https://rickandmortyapi.com/api/episode/1"],
                    "url": "https://rickandmortyapi.com/api/character/2",
                    "created": "2017-11-04T18:48:46.250Z"
                }
            ]
        }
    """.trimIndent()

    val characterRickSanchez = """
        {
            "id": 1,
            "name": "Rick Sanchez",
            "status": "Alive",
            "species": "Human",
            "type": "",
            "gender": "Male",
            "origin": {"name": "Earth", "url": ""},
            "location": {"name": "Earth", "url": ""},
            "image": "https://example.com/1.jpeg",
            "episode": ["https://rickandmortyapi.com/api/episode/1"],
            "url": "https://rickandmortyapi.com/api/character/1",
            "created": "2017-11-04T18:48:46.250Z"
        }
    """.trimIndent()

    val characterRickId42 = """
        {
            "id": 42,
            "name": "Rick",
            "status": "Alive",
            "species": "Human",
            "type": "",
            "gender": "Male",
            "origin": {"name": "Earth", "url": ""},
            "location": {"name": "Earth", "url": ""},
            "image": "https://example.com/42.jpeg",
            "episode": ["https://rickandmortyapi.com/api/episode/1"],
            "url": "https://rickandmortyapi.com/api/character/42",
            "created": "2017-11-04T18:48:46.250Z"
        }
    """.trimIndent()

    val characterMortySmith = """
        {
            "id": 1,
            "name": "Morty Smith",
            "status": "Alive",
            "species": "Human",
            "type": "",
            "gender": "Male",
            "origin": {"name": "Earth", "url": ""},
            "location": {"name": "Earth", "url": ""},
            "image": "https://example.com/1.jpeg",
            "episode": ["https://rickandmortyapi.com/api/episode/1"],
            "url": "https://rickandmortyapi.com/api/character/1",
            "created": "2017-11-04T18:48:46.250Z"
        }
    """.trimIndent()
}
