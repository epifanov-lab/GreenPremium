package com.lab.greenpremium.data.entity

data class Contacts(val office: OfficeContact,
                    val managers: List<ManagerContact>)

data class OfficeContact(val name: String,
                  val email: String,
                  val phone: String)

data class ManagerContact(val id: String,
                          val name: String,
                          val email: String,
                          val phone: String,
                          val position: String,
                          val photo: String)