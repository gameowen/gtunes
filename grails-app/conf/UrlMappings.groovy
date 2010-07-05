class UrlMappings {
   static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
            // apply constraints here
         }
      }
      "/" (controller: "store", action: 'shop')
      "/genre/$name" (controller: "store", action="genre")
	  "500"(view:'/error')
	}
}
