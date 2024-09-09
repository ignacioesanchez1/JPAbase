package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //System.out.println("en marcha Alberto");

        try {
            entityManager.getTransaction().begin();

            Factura factura1 = new Factura();
            factura1.setNumero(12);
            factura1.setFecha("10/08/2020");

            Domicilio domicilio = new Domicilio("San MAartín", 1222);
            Cliente cliente = new Cliente("Pablo", "Muñoz", 12345678);
            cliente.setDomicilio(domicilio);
            domicilio.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoría perecederos = new Categoría("Perecederos");
            Categoría lacteos = new Categoría("Lacteos");
            Categoría limpieza = new Categoría("Limpieza");


            Artículo artículo1 = new Artículo(200, "Yogurt Ser sabor frutilla", 20);
            Artículo artículo2 = new Artículo(300, "Detergente Magistral", 80);

            artículo1.getCategorías().add(perecederos);
            artículo1.getCategorías().add(lacteos);
            lacteos.getArtículos().add(artículo1);
            perecederos.getArtículos().add(artículo1);

            artículo2.getCategorías().add(limpieza);
            limpieza.getArtículos().add(artículo2);


            DetalleFactura detalle1 = new DetalleFactura();
            detalle1.setArtículo(artículo1);
            detalle1.setCantidad(2);
            detalle1.setSubtotal(40);

            artículo1.getDetalles().add(detalle1);
            factura1.getDetalles().add(detalle1);
            detalle1.SetFactura(factura1);

            DetalleFactura detalle2 = new DetalleFactura();
            detalle2.setArtículo(artículo2);
            detalle2.setCantidad(1);
            detalle2.setSubtotal(80);

            artículo2.getDetalles().add(detalle2);
            factura1.getDetalles().add(detalle1);
            detalle2.SetFactura(factura1);

            factura1.setTotal(120);

            entityManager.persist(factura1);

            entityManager.flush();
            entityManager.getTransaction().commit();


        }catch (Exception e) {
            entityManager.getTransaction().rollback();

        }


        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}