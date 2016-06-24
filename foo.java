package com.dotsub.vtms.acceptance;

import com.dotsub.vtms.AbstractAcceptanceTests;
import com.dotsub.vtms.WithOrganization;
import com.dotsub.vtms.acceptance.pageobject.TrackTranslatePage;

import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 2016-05-24.
 */
public class TrackTranslateAcceptanceTests extends AbstractAcceptanceTests {

    @Test
    @WithOrganization("dotsub")
    @WithUserDetails("owner@dotsub.com")
    public void testTrackTranslatePageLoads() throws Exception {
        // test page loading
        new TrackTranslatePage(driver).get(12, 12, 18)
            .waitForVideoPlayer();
    }

    @Test
    @WithOrganization("dotsub")
    @WithUserDetails("owner@dotsub.com")
    public void testTrackTranslateHasItems() throws Exception {
        new TrackTranslatePage(driver).get(12, 12, 18)
            .checkForCaption("The Peach Open Move Project Presents", 1)
            .checkForTranslation("Le projet de déménagement Peach Open Presents", 1)
            .checkForDirection("ltr", 1);
    }

    @Test
    @WithOrganization("dotsub")
    @WithUserDetails("owner@dotsub.com")
    public void testTrackTranslateHasDirection() throws Exception {
        new TrackTranslatePage(driver).get(12, 14, 20)
            .checkForCaption("The Peach Open Move Project Presents", 1)
            .checkForTranslation("ويعرض مشروع الفيلم الخوخ المفتوحة", 1)
            .checkForDirection("rtl", 1);
    }
}
