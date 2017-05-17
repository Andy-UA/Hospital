<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<style>
    .bs-docs-footer {
        padding-top: 25px;
        padding-bottom: 25px;
        margin-top: 60px;
        color: #99979c;
        text-align: left;
        background-color: #444444;
    }
    .table-button {
        padding: 0px;
        margin: 0px;
    }
    .table-icon {
        font-size: 15px;
    }
    .request-control {
        margin-left: 0px;
        margin-right: 10px;
    }
    .request-bar {
        padding-left: 0px;
        padding-right: 0px;
        padding-top: 5px;
        padding-bottom: 0px;

        margin-left: 0px;
        margin-right: 0px;
        margin-top: 0px;
        margin-bottom: 5px;
        background-color: whitesmoke;
    }
    .recordid-label {
        font-style: italic;
        font-size: small;
        color: silver;
        vertical-align: super;
    }
</style>
<footer class="bs-docs-footer">
    <div id="footer" class="container-fluid">
    <div class="container">
        <div class="row">

            <div class="col-sm-6 col-md-4" id="contact">

                <div class="region region-footer-top-left">
                    <div id="block-block-6" class="block block-block">
                        <div class="content">
                            <h5><a href="tel:+380632288291">+380632288291</a></h5>
                            <p>Our representatives are available to schedule your appointment on weekdays from 8am&nbsp;to 8pm&nbsp;and weekends from 9am&nbsp;to 5pm&nbsp;You may also leave a voicemail message after regular business hours, and we will return your call.</p>
                            <p><a href="${pageContext.request.contextPath}/appointmentList"><strong>Request an appointment</strong></a></p>
                            <p><em>If this is a medical emergency, please call 103.</em></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-5">
                <div class="region region-footer-menu-1">
                    <div id="block-menu-menu-footer-menu-1" class="block block-menu">
                        <div class="content">
                            <ul class="list-unstyled sitelinks">
                                <li><a href="http://jobs.northwell.edu/" title="" target="_blank">Jobs</a></li>
                                <li><a href="/contact" title="">Contact us</a></li>
                                <li><a href="/about" title="">Newsroom</a></li>
                                <li><a href="/patientList" title="">Patient portal</a></li>
                                <li><a href="/volunteer" title="">Volunteer</a></li>
                                <li><a href="/ways-to-give" title="" target="_blank">Ways to give</a></li>
                                <li><a href="/paybill" title="" target="_blank">Pay a bill</a></li>
                                <li><a href="/healthcare-professional-resources" title="">Healthcare professional resources</a></li>
                                <li><a href="/alerts" title="">Alerts</a></li>
                                <li><a href="/diversity-and-inclusion" title="">Diversity &amp; inclusion</a></li>
                                <li><a href="/physician-partners" title="">Health Physician Partners</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id="block-block-21" class="block block-block">
                        <div class="content">
                            <p>
                                <a class="btn btn-social-icon btn-facebook" href="https://www.facebook.com/hospitalis" target="_blank"><span class="fa fa-facebook">&nbsp;</span></a>
                                <a class="btn btn-social-icon btn-twitter" href="https://www.twitter.com/hospitalis" target="_blank"><span class="fa fa-twitter">&nbsp;</span></a>
                                <a class="btn btn-social-icon btn-instagram" href="http://instagram.com/hospitalis" target="_blank"><span class="fa fa-instagram">&nbsp;</span></a>
                                <a class="btn btn-social-icon btn-linkedin" href="http://www.linkedin.com/company/hospitalis" target="_blank"><span class="fa fa-linkedin">&nbsp;</span></a>
                                <a class="btn btn-social-icon btn-google" href="https://www.google.com/" target="_blank"><span class="fa fa-google">&nbsp;</span></a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-md-3 text-right">
                <div class="region region-footer-bottom-right">
                    <div id="block-block-11" class="block block-block">
                        <div class="content">
                            <p><a class="nw-btn nw-btn--inline employees" href="${pageContext.request.contextPath}/staffList" style="font-weight: 600!important;">For employees</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="corporate" class="row">
            <div class="col-sm-12">
                <hr/>
                <div class="region region-footer-bottom-left">
                    <div id="block-block-1" class="block block-block">
                        <div class="content">
                            <p class="copy">© Copyright 2017&nbsp;Andrew Stupnitsky KP-31</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</footer>
