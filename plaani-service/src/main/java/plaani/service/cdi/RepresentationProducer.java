package plaani.service.cdi;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.enterprise.inject.Produces;

@SuppressWarnings("UnusedDeclaration")
public class RepresentationProducer {

    public static final RepresentationFactory representationFactory = new StandardRepresentationFactory()
            .withFlag(RepresentationFactory.PRETTY_PRINT);

    @Produces
    public RepresentationFactory createPresentation() {
        return representationFactory;
    }
}
