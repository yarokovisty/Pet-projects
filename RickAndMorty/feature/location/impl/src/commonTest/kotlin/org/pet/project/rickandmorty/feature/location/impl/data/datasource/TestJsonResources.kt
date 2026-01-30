package org.pet.project.rickandmorty.feature.location.impl.data.datasource

object TestJsonResources {

    val locationListSingleLocation = """
        {
            "results": [
                {
                    "id": 1,
                    "name": "Earth (C-137)",
                    "type": "Planet",
                    "dimension": "Dimension C-137",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/38",
                        "https://rickandmortyapi.com/api/character/45"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/1",
                    "created": "2017-11-10T12:42:04.162Z"
                }
            ]
        }
    """.trimIndent()

    val locationListTwoLocations = """
        {
            "results": [
                {
                    "id": 1,
                    "name": "Earth (C-137)",
                    "type": "Planet",
                    "dimension": "Dimension C-137",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/38"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/1",
                    "created": "2017-11-10T12:42:04.162Z"
                },
                {
                    "id": 2,
                    "name": "Abadango",
                    "type": "Cluster",
                    "dimension": "unknown",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/6"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/2",
                    "created": "2017-11-10T13:06:38.182Z"
                }
            ]
        }
    """.trimIndent()

    val locationListEmpty = """
        {
            "results": []
        }
    """.trimIndent()

    val locationEarthC137 = """
        {
            "results": [
                {
                    "id": 1,
                    "name": "Earth (C-137)",
                    "type": "Planet",
                    "dimension": "Dimension C-137",
                    "residents": [
                        "https://rickandmortyapi.com/api/character/38",
                        "https://rickandmortyapi.com/api/character/45"
                    ],
                    "url": "https://rickandmortyapi.com/api/location/1",
                    "created": "2017-11-10T12:42:04.162Z"
                }
            ]
        }
    """.trimIndent()

    val locationCitadelOfRicks = """
        {
            "results": [
                {
                    "id": 3,
                    "name": "Citadel of Ricks",
                    "type": "Space station",
                    "dimension": "unknown",
                    "residents": [],
                    "url": "https://rickandmortyapi.com/api/location/3",
                    "created": "2017-11-10T13:08:13.191Z"
                }
            ]
        }
    """.trimIndent()

    val residentBethSmith = """
        {
            "id": 38,
            "name": "Beth Smith",
            "image": "https://rickandmortyapi.com/api/character/avatar/38.jpeg"
        }
    """.trimIndent()

    val residentRickSanchez = """
        {
            "id": 1,
            "name": "Rick Sanchez",
            "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        }
    """.trimIndent()

    val residentId42 = """
        {
            "id": 42,
            "name": "Character 42",
            "image": "https://rickandmortyapi.com/api/character/avatar/42.jpeg"
        }
    """.trimIndent()
}
