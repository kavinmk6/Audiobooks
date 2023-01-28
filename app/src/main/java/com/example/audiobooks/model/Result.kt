package com.example.audiobooks.model

////I have included all the variables eventhough we need only Podcast variable, because we dont need any other variables as per the given requirements.
data class Result(
    val audio: String,
    val audio_length_sec: Int,
    val description_highlighted: String,
    val description_original: String,
    val explicit_content: Boolean,
    val guid_from_rss: String,
    val id: String,
    val image: String,
    val itunes_id: Int,
    val link: String,
    val listennotes_url: String,
    val podcast:Podcast,
    val pub_date_ms: Long,
    val rss: String,
    val thumbnail: String,
    val title_highlighted: String,
    val title_original: String,
    val transcripts_highlighted: List<Any>
)