package com.ahoubouby.encupsilation.app_1
trait PhoneValidator {
  def validePhoneNumber(phoneNumber: String): Boolean = phoneNumber.length > 10
}
