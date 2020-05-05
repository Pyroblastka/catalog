package com.pyro.service.classification;

import com.pyro.entities.classification.Family;
import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Order;
import com.pyro.repositories.classification.FamilyRepository;
import com.pyro.repositories.classification.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    KlassService klassService;

    @Transactional
    public Order save(Order order) {

        Order localObject = orderRepository.findByName(order.getName());
        if (localObject != null) {
            return localObject;
        }

        Klass klass = order.getKlass();
        if(klass!=null)
        {
            order.setKlass(klassService.save(klass));
        }

        order = orderRepository.saveAndFlush(order);
        return order;
    }
}
