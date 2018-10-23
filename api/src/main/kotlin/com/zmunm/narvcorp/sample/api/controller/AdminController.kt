package com.zmunm.narvcorp.sample.api.controller

import com.zmunm.narvcorp.sample.core.dto.*
import com.zmunm.narvcorp.sample.core.mysql.dao.AdminDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class AdminController {
    @Autowired
    lateinit var adminDAO : AdminDAO

    @Autowired
    lateinit var bCryptPasswordEncoder: PasswordEncoder

    @PostMapping("")
    fun insert (@RequestBody requestBody:Map<String,Any>) =
            adminDAO.insert(Admin(requestBody[ID].toString()).apply {
                requestBody[PASSWORD].toString().run {
                    password = bCryptPasswordEncoder.encode(this)
                }
                name = requestBody[NAME].toString()
                email = requestBody[EMAIL].toString()
            })

    @GetMapping("")
    fun select (@RequestParam id:String, @RequestParam password:String) = adminDAO.selectPw(id)
            .lastOrNull()
            ?.takeIf { bCryptPasswordEncoder.matches(password,it.password) }
            ?.apply {
                adminDAO.selectOne(
                        id
                ).last().let {
                    name = it.name
                    email = it.email
                }
            }

    @DeleteMapping("")
    fun delete (@RequestParam id:String) = adminDAO.delete(id)
}
