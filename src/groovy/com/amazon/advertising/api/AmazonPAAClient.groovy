/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amazon.advertising.api

/**
 *
 * @author rpieciorak
 */
class AmazonPAAClient {

    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */

    //private static final String AWS_ACCESS_KEY_ID = "YOUR_ACCESS_KEY_ID_HERE";
    private String accessKeyId

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    //private static final String AWS_SECRET_KEY = "YOUR_SECRET_KEY_HERE";
    private String secretKey

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     *
     *      US: ecs.amazonaws.com
     *      CA: ecs.amazonaws.ca
     *      UK: ecs.amazonaws.co.uk
     *      DE: ecs.amazonaws.de
     *      FR: ecs.amazonaws.fr
     *      JP: ecs.amazonaws.jp
     *
     */
    private String endpoint = "ecs.amazonaws.com"

    private SignedRequestHelper helper = null

      //helper = new SignedRequestHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY, AWS_SECRET_KEY)

    def getAlbumArtUrl(String artist, String album) {
       helper = helper ?: new SignedRequestHelper(endpoint, accessKeyId, secretKey)

       Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemSearch");
        params.put("SearchIndex", "Music");
        params.put("Artist", artist);
        params.put("Title", album);
        params.put("ResponseGroup", "Images");

        def requestUrl = helper.sign(params);
        //log.info "Amazon PAA request - ${requestUrl}"
        def xml = requestUrl.toURL().text
        return new XmlSlurper(false, false).parseText(xml)?."Items"[0]?."Item"[0]?.LargeImage?."URL"
    }

}