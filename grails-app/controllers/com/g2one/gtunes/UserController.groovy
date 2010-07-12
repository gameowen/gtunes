package com.g2one.gtunes

class UserController {

   def mailService

   def register = {
      if (request.method == "POST") {
         def u = new User(params)
         if (User.findByLogin(params.login)) {
            return [ user: u, message: 'user.already.exsists' ]
         } else if (u.password != params.confirm) {
            u.errors.rejectValue("password", "user.password.dontmatch")
            return [ user: u ]
         } else if (u.email != params.confirmEmail) {
            u.errors.rejectValue("email", "user.email.dontmatch")
         } else if (u.save()) {
            session.user = u
            try {
               sendMail {
                  to u.email
                  subject "Registration Confirmation"
                  body view: '/emails/confirmRegistration', model: [ user: u ]
               }
            } catch(Exception e) {
               log.error "Problem sending email ${e.message}", e
            }
            if (params.newsletter) {
               new Subscription(type: SubscriptionType.NEWSLETTER, user: u).save()
            }
            redirect(controller: "store")
         } else {
            return [ user: u ]
         }
      }
   }

   def login = {
      LoginCommand cmd ->
      if (request.method == 'POST') {
         if (!cmd.hasErrors()) {
            session.user = cmd.getUser()
            render(template: 'welcome')
         } else {
            render(template: 'loginForm', model: [loginCmd: cmd])
         }
      } else {
         render(view: '/store/index')
      }
   }

   def logout = {
		session.invalidate()
		redirect(controller:"store")
	}

}


class LoginCommand {
   String login
   String password
   private u

   static constraints = {
      login blank: false, validator: {
         val, cmd ->
         if (!cmd.user) {
            return "user.not.found"
         }
      }
      password blank: false, validator: {
         val, cmd ->
         if (cmd.user && cmd.user.password != val) {
            return "user.password.invalid"
         }
      }
   }

   User getUser() {
      if (!u && login) {
         u = User.findByLogin(login, [fetch: [purchasedSongs: 'join']])
      }
      return u
   }
}
