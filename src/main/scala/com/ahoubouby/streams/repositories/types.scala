package com.ahoubouby.streams.repositories

case class EmailAddress(email: String)
case class Person(id: String, name: String, email: String)
case class Project(id: String, name: String)
case class Schedule(id: String, project: Project)
