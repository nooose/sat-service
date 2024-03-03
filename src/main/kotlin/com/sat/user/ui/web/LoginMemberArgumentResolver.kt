package com.sat.user.ui.web

import com.sat.common.config.security.AuthenticatedMember
import com.sat.user.domain.LoginMemberInfo
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginMemberArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginMember::class.java) &&
                parameter.parameterType.isAssignableFrom(LoginMemberInfo::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): LoginMemberInfo {
        val principal = SecurityContextHolder.getContext().authentication.principal as AuthenticatedMember
        return LoginMemberInfo(
            id = principal.id,
            name = principal.name,
            nickname = principal.nickName,
            email = principal.email,
        )
    }


}
