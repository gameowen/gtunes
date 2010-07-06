package com.g2one.gtunes

import com.amazon.advertising.api.AmazonPAAClient
import com.amazonaws.a2s.*
import net.sf.ehcache.Element

class AlbumArtService {

   static transactional = false
   static DEFAULT_ALBUM_ART_IMAGE = '/images/no-album-art.gif'

   String accessKeyId
   String secretAccessKey
   String endpoint
   def albumArtCache

   String getAlbumArt(String artist, String album) {
      if (accessKeyId) {
         if (album && artist) {
            def key = new AlbumArtKey(album: album, artist: artist)
            def url = albumArtCache?.get(key)?.value
            if (!url) {
               try {
                  def client = new AmazonPAAClient(
                     endpoint: endpoint,
                     accessKeyId: accessKeyId,
                     secretKey: secretAccessKey
                  )
                  url = client.getAlbumArtUrl(artist, album)
                  // get the URL to the amazon image (if one was return)
                  if (url) {
                     albumArtCache?.put(new Element(key, url))
                  }
               } catch (Exception e) {
                  log.error "Problem communicating with Amazon: ${e.message}", e
                  return DEFAULT_ALBUM_ART_IMAGE
               }
            }
            return url
         } else {
            log.warn "Album title and Artist name may not be null or empty"
            return DEFAULT_ALBUM_ART_IMAGE
         }
      } else {
         log.warn """No Amazon access key specified.
         Set [beans.albumArtService.accessKeyId] in Config.groovy"""
         return DEFAULT_ALBUM_ART_IMAGE
      }
   }
}


class AlbumArtKey implements Serializable {
   String artist
   String album

   boolean equals(other) {
      artist.equals(other.artist) && album.equals(other.album)
   }

   int hashCode() {
      artist.hashCode() + album.hashCode()
   }
}