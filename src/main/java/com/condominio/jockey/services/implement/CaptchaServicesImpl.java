package com.condominio.jockey.services.implement;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.condominio.jockey.beans.Captcha;

@Service
public class CaptchaServicesImpl {

	private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";
	private static final String RECAPTCHASECRET = "6LeEvFsUAAAAAIGhFQ-eTNZrWrGElRQ7CIkuIyUs";

	private CaptchaServicesImpl() {
		super();
	}

	public static boolean validateCaptcha(String captchaResponse) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", RECAPTCHASECRET);
		requestMap.add("response", captchaResponse);

		Captcha apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, Captcha.class);
		if (apiResponse == null) {
			return false;
		}
		return apiResponse.isSuccess();
	}
}