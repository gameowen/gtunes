package com.g2one.gtunes

import com.amazon.advertising.api.AmazonPAAClient
import net.sf.ehcache.Element

class AlbumArtService {

   static transactional = false
   static DEFAULT_ALBUM_ART_IMAGE = 'images/no-album-art.gif'

   String accessKeyId
   String secretKey
   String endpoint
   def albumArtCache

   String getAlbumArt(String artist, String album) {
      if (accessKeyId && secretKey && endpoint) {
         if (album && artist) {
            def key = new AlbumArtKey(album: album, artist: artist)
            def url = albumArtCache?.get(key)?.value
            if (!url) {
               try {
                  def client = new AmazonPAAClient(
                     endpoint: endpoint,
                     accessKeyId: accessKeyId,
                     secretKey: secretKey
                  )
                  url = client.getAlbumArtUrl(artist, album)
                  // get the URL to the amazon image (if one was return)
                  if (!url?.isEmpty()) {
                     log.info "new cache entry: [ ${url}. ${key} ]"
                     albumArtCache?.put(new Element(key, url.toString()))
                  } else {
                     log.warn "No album art found on Amazon"
                     return DEFAULT_ALBUM_ART_IMAGE
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
         log.warn """No Amazon access key, secret key or endpoint specified.
         Set [
            beans.albumArtService.accessKeyId,
            beans.albumArtService.secretKey,
            beans.albumArtService.endpoint,
         ] in Config.groovy"""
         return DEFAULT_ALBUM_ART_IMAGE
      }
   }
}


class AlbumArtKey implements java.io.Serializable {
   String artist
   String album

   boolean equals(other) {
      artist.equals(other.artist) && album.equals(other.album)
   }

   int hashCode() {
      artist.hashCode() + album.hashCode()
   }

   String toString() { "$artist - $album" }
}