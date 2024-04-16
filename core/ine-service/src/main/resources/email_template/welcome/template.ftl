<#import "../layout.ftl" as layout>
<@layout.email>
  <h2>${messages['email.template.welcome.subject']}</h2>
  <p>${messages('email.template.welcome.hello', user_name)}</p>
  <p>${messages('email.template.welcome.instruction1', mail, password)}</p>
  <p>${messages['email.template.welcome.instruction2']}</p>
  <a href="${base_url}">${messages['email.template.welcome.click_here']}</a>
</@layout.email>