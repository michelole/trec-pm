package at.medunigraz.imi.bst.trec.query;

import at.medunigraz.imi.bst.trec.model.Challenge;
import at.medunigraz.imi.bst.trec.model.Task;
import at.medunigraz.imi.bst.trec.model.Topic;
import at.medunigraz.imi.bst.trec.model.TopicSet;
import de.julielab.ir.umls.UmlsRelationsProvider;
import de.julielab.ir.umls.UmlsSynsetProvider;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DiseaseUmlsHypernymQueryDecoratorTest {
    private class UmlsSynsetTestProvider extends UmlsSynsetProvider{
        private UmlsSynsetTestProvider() {
            super("src/test/resources/umls/melanoma.synset", UmlsSynsetProvider.DEFAULT_SEPARATOR, false, false);
        }
    }
    private class UmlsRelationsTestProvider extends UmlsRelationsProvider{
        private UmlsRelationsTestProvider() {
            super("src/test/resources/umls/melanoma.relations",  false);
        }
    }
    @Test
    public void testPmTopics() {
        DummyElasticSearchQuery dummyQuery = new DummyElasticSearchQuery();
        final DiseaseUmlsHypernymQueryDecorator decorator = new DiseaseUmlsHypernymQueryDecorator(dummyQuery);
        decorator.setSynsetProvider(new UmlsSynsetTestProvider());
        decorator.setRelationsProvider(new UmlsRelationsTestProvider());
        final TopicSet topicSet = new TopicSet(new File("src/main/resources/topics/topics2018.xml"), Challenge.TREC_PM, Task.PUBMED, 2017);
        for (Topic topic : topicSet.getTopics()) {
            decorator.expandTopic(topic);
            if (topic.getDisease().equals("melanoma"))
                assertThat(topic.getDiseaseHypernyms()).contains("melanoma parent");
        }
    }
}