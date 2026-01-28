package org.pet.project.rickandmorty.feature.episode.impl.data.datasource

internal object EpisodeTestJsonResources {

    val singleEpisode = """
        {
            "id": 1,
            "name": "Pilot",
            "air_date": "December 2, 2013",
            "episode": "S01E01"
        }
    """.trimIndent()

    val multipleEpisodes = """
        [
            {
                "id": 1,
                "name": "Pilot",
                "air_date": "December 2, 2013",
                "episode": "S01E01"
            },
            {
                "id": 2,
                "name": "Lawnmower Dog",
                "air_date": "December 9, 2013",
                "episode": "S01E02"
            },
            {
                "id": 3,
                "name": "Anatomy Park",
                "air_date": "December 16, 2013",
                "episode": "S01E03"
            }
        ]
    """.trimIndent()

    val episodeListSinglePage = """
        {
            "info": {
                "count": 51,
                "pages": 3,
                "next": "https://rickandmortyapi.com/api/episode?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Pilot",
                    "air_date": "December 2, 2013",
                    "episode": "S01E01"
                },
                {
                    "id": 2,
                    "name": "Lawnmower Dog",
                    "air_date": "December 9, 2013",
                    "episode": "S01E02"
                }
            ]
        }
    """.trimIndent()

    val episodeListTwoEpisodes = """
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
                    "name": "Pilot",
                    "air_date": "December 2, 2013",
                    "episode": "S01E01"
                },
                {
                    "id": 2,
                    "name": "Lawnmower Dog",
                    "air_date": "December 9, 2013",
                    "episode": "S01E02"
                }
            ]
        }
    """.trimIndent()

    val episodeListEmpty = """
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

    val episodeListTwoPages = """
        {
            "info": {
                "count": 51,
                "pages": 2,
                "next": "https://rickandmortyapi.com/api/episode?page=2",
                "prev": null
            },
            "results": [
                {
                    "id": 1,
                    "name": "Pilot",
                    "air_date": "December 2, 2013",
                    "episode": "S01E01"
                }
            ]
        }
    """.trimIndent()

    val episodePilot = """
        {
            "id": 1,
            "name": "Pilot",
            "air_date": "December 2, 2013",
            "episode": "S01E01"
        }
    """.trimIndent()

    val episodeId42 = """
        {
            "id": 42,
            "name": "The Rickshank Rickdemption",
            "air_date": "April 1, 2017",
            "episode": "S03E01"
        }
    """.trimIndent()
}
