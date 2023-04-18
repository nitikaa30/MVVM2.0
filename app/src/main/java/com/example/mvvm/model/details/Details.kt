package com.example.mvvm.model.details

data class Details(val `data`: ArrayList<DetailsData>,
                   val page: Int,
                   val per_page: Int,
                   val total_pages: Int,
                   val totalrecord: Int)
