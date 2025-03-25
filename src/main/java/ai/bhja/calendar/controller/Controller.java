package ai.bhja.calendar.controller;

import ai.bhja.calendar.model.OAuthModel;
import ai.bhja.calendar.service.AzureAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {

  /**
   * Generates. the Azure Login authorization Url
   */
  private final AzureAuthService authService;


  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("url", authService.getAuthUrl());
    return "index";
  }

  /**
   * Exchanges the code for access token
   *
   * @param code
   * @return
   */

  @GetMapping("/exchange")
  public ModelAndView authExchange(@RequestParam String code) {
    log.info("Code received in callback {}", code);
    OAuthModel oauthModel = authService.authExchangeCode(code);
    ModelAndView mv = new ModelAndView("token");
    mv.addObject("oauthModel", oauthModel);
    return mv;
  }


  /**
   * Uses the refresh token and retrieves the new access token.
   *
   * @param refreshToken
   * @param model
   * @return
   */
  @GetMapping("/refresh")
  public String refresh(@RequestParam String refreshToken, Model model) {
    OAuthModel authModel = authService.refreshToken(refreshToken);
    model.addAttribute("oauthModel", authModel);
    return "token";
  }

}
