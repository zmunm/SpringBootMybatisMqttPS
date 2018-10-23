package com.zmunm.narvcorp.sample.api

import com.zmunm.narvcorp.sample.api.controller.AdminController
import com.zmunm.narvcorp.sample.core.dto.EMAIL
import com.zmunm.narvcorp.sample.core.dto.ID
import com.zmunm.narvcorp.sample.core.dto.NAME
import com.zmunm.narvcorp.sample.core.dto.PASSWORD
import com.zmunm.narvcorp.sample.core.mysql.dao.AdminDAO
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import java.security.MessageDigest

internal enum class AdminTester(
		val id:String,
		val password:String,
		val email:String
){
	AdminTest("testId","testPw","test@test.tt")
}

private val ADMIN_TESTER : AdminTester = AdminTester.AdminTest

@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
class AdminTests {
	@Autowired
	private lateinit var adminDAO: AdminDAO

	@Autowired
	private lateinit var bCryptPasswordEncoder: PasswordEncoder

	private fun sha256encode(password:String) = MessageDigest.getInstance("SHA-256")
			.digest(password.toByteArray()).fold("") { str, it->str + "%02x".format(it)}

	private val adminController by lazy {
		AdminController().also {
			it.adminDAO = adminDAO
			it.bCryptPasswordEncoder = bCryptPasswordEncoder
		}
	}

	@Test
	fun `01insert`() = ADMIN_TESTER.run {adminController.insert(mapOf(
			ID to id,
			PASSWORD to sha256encode(password),
			NAME to name,
			EMAIL to email
	))}

	@Test
	fun `02select`():Unit = ADMIN_TESTER.run {adminController.select(id, sha256encode(password))?.let {
			assertEquals(name,it.name)
			assertEquals(email,it.email)
		}
	}

	@Test
	fun `03delete`() = ADMIN_TESTER.run {adminController.delete(id)
			.run { adminDAO.restore(id) }
	}
}
