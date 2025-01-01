package info.zoio.langchain4jside.config.langchain;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import info.zoio.langchain4jside.extend.langchain.agent.AssistantAgent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

/**
 * AI配置
 *
 * @author humyna
 */
@Configuration
public class LangChainAiConfg {

    @Bean
    AssistantAgent createAssitant(ChatLanguageModel chatLanguageModel,
                                  Retriever<TextSegment> retriever) {
        return AiServices.builder(AssistantAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .retriever(retriever)
                .build();
    }

    @Bean
    Retriever<TextSegment> retriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        int maxResultsRetrieved = 1;
        double minScore = 0.6;

        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, maxResultsRetrieved, minScore);
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }
    @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {
       EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        Resource resource = resourceLoader.getResource("classpath:assistant-rule.txt");
        Document document = loadDocument(resource.getFile().toPath(), new TextDocumentParser());

        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);

        return embeddingStore;
    }
}
