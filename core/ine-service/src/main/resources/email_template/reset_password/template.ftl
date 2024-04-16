<#import "../layout.ftl" as layout>
<@layout.email>
  <h2>${messages['email.template.reset_password.subject']}</h2>
  <p>${messages('email.template.reset_password.hello', user_name)}</p>
  <p>${messages['email.template.reset_password.instruction1']}</p>
  <p>${messages('email.template.reset_password.instruction2', password)}</p>
</@layout.email>