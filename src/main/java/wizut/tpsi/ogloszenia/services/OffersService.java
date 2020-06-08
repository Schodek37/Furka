package wizut.tpsi.ogloszenia.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wizut.tpsi.ogloszenia.jpa.*;
import wizut.tpsi.ogloszenia.web.OfferFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
@Transactional
public class OffersService {

    @PersistenceContext
    private EntityManager em;

    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }

    public CarModel getModel(int id) {
        return em.find(CarModel.class, id);
    }

    public List<CarManufacturer> getCarManufacturers() {
        String jpql = "select cm from CarManufacturer cm order by cm.name";
        TypedQuery<CarManufacturer> query = em.createQuery(jpql, CarManufacturer.class);
        List<CarManufacturer> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels() {
        String jpql = "select cm from CarModel cm order by cm.name";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        List<CarModel> result = query.getResultList();
        return result;
    }

    public List<BodyStyle> getBodyStyles() {
        String jpql = "select bs from BodyStyle bs order by bs.name";
        TypedQuery<BodyStyle> query = em.createQuery(jpql, BodyStyle.class);
        List<BodyStyle> result = query.getResultList();
        return result;
    }

    public List<FuelType> getFuelTypes() {
        String jpql = "select ft from FuelType ft order by ft.name";
        TypedQuery<FuelType> query = em.createQuery(jpql, FuelType.class);
        List<FuelType> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";

        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }

    public List<Offer> getOffers() {
        String jpql = "select offer from Offer offer order by offer.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        return query.getResultList();
    }

    public List<Offer> getOffersByModel(int modelId) {
        String jpql = "select offer from Offer offer where offer.model.id = :id order by offer.title";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);

        return query.getResultList();
    }

    public List<Offer> getOffersByManufacturer(int manufacturerId) {
        String jpql = "select offer from Offer offer where offer.model.manufacturer.id = :id order by offer.title";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }

    public Offer getOffer(int offerId) {
        String jpql = "select offer from Offer offer where offer.id = :id";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", offerId);

        return query.getSingleResult();
    }


    public Offer createOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }

    public Offer deleteOffer(Integer id) {
        Offer offer = em.find(Offer.class, id);
        em.remove(offer);
        return offer;
    }

    public Offer saveOffer(Offer offer) {
        return em.merge(offer);
    }

    public List<Offer> getOffers(OfferFilter filter){

        String jpql = "select offert from Offer offert where 1=1 ";
        if(filter.getModelId()!=null){
            jpql = jpql.concat("and offert.model.id = :id ");
        }
        if(filter.getManufacturerId()!=null){
            jpql = jpql.concat("and offert.model.manufacturer.id = :id2 ");
        }
        if(filter.getDateFrom()!=null){
            jpql = jpql.concat("and offert.year >= :yr1 ");
        }
        if(filter.getDateTo()!=null){
            jpql = jpql.concat("and offert.year <= :yr2 ");
        }
        if(filter.getFuelTypeId()!=null){
            jpql = jpql.concat("and offert.fuelType.id = :fuel ");
        }


        TypedQuery<Offer> query = em.createQuery(jpql,Offer.class);
        if(filter.getModelId()!=null){
            query.setParameter("id", filter.getModelId());
        }
        if(filter.getManufacturerId()!=null){
            query.setParameter("id2", filter.getManufacturerId());
        }
        if(filter.getDateFrom()!=null){
            query.setParameter("yr1", filter.getDateFrom());
        }
        if(filter.getDateTo()!=null){
            query.setParameter("yr2", filter.getDateTo());
        }
        if(filter.getFuelTypeId()!=null){
            query.setParameter("fuel", filter.getFuelTypeId());
        }
        List<Offer> result = query.getResultList();
        return result;
    }


}