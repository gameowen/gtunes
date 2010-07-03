class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/" (controller: "store")
      "/genre/$name" (controller: "store", action="genre")
	  "500"(view:'/error')
	}
}
