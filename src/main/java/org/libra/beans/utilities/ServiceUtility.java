package org.libra.beans.utilities;

import org.libra.entities.Entities;
import org.libra.entities.implementation.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Component
public class ServiceUtility
{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityUtility authorityUtility;

    public UserEntity encodeUserEntityPassword(UserEntity userEntity)
    {
        if (userEntity.getPassword() != null)
        {
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        }
        return userEntity;
    }
    public <T extends Entities> T setAuthenticatedEmailPropertyValue(T targetEntity) throws Exception
    {
        targetEntity.getClass().getMethod("setEmail", String.class).invoke(targetEntity, authorityUtility.getCurrentAuthenticationEmail());
        return targetEntity;
    }
    public <T extends Entities> T patchEntity(T targetEntity, T patchingEntity) throws Exception
    {
        if (targetEntity.getClass().equals(patchingEntity.getClass()))
        {
            for(Method method : targetEntity.getClass().getDeclaredMethods())
            {
                if (method.getName().startsWith("get") && (patchingEntity.getClass().getMethod(method.getName()).invoke(patchingEntity) != null))
                {
                    Class<?> argumentClass = targetEntity.getClass().getMethod(method.getName()).getReturnType();
                    targetEntity.getClass().getMethod("set" + method.getName().substring(3), argumentClass)
                            .invoke(targetEntity, patchingEntity.getClass().getMethod(method.getName()).invoke(patchingEntity));
                }
            }
        }
        else
        {
            throw new ReflectiveOperationException("These are objects of different classes");
        }
        return targetEntity;
    }

    @Autowired
    public ServiceUtility(BCryptPasswordEncoder bCryptPasswordEncoder, AuthorityUtility authorityUtility)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityUtility = authorityUtility;
    }
}