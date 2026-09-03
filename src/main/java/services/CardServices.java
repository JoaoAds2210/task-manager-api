package services;

import dtos.card.CardRequest;
import dtos.card.CardResponse;
import entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repository.CardRepository;
import mappers.CardMapper;

import java.util.List;

@Service
public class CardServices {

    @Autowired
    private CardRepository cardRepository;

    public List<CardResponse> findAll(){
        List<Card> cards = cardRepository.findAll();

        return cards.stream()
                .map(CardMapper::toResponse)
                .toList();
    }

    public CardResponse findById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card não encontrado"));
        return CardMapper.toResponse(card);
    }

    public CardResponse create (Long boardId , CardRequest cardRequest) {
        Card card = CardMapper.toEntity(cardRequest, boardId);
        Card cardSaved = cardRepository.save(card);
        return CardMapper.toResponse(cardSaved);
    }

    public CardResponse update(Long id, CardRequest cardRequest){
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card não encontrado"));

        card.setTitle(cardRequest.title());
        card.setDescription(cardRequest.description());
        card.setStatus(cardRequest.status());
        card.setPriority(cardRequest.priority());
        card.setPosition(cardRequest.position());
        card.setAssigneeName(cardRequest.assigneeName());
        card.setDueDate(cardRequest.dueDate());

        Card cardUpdated = cardRepository.save(card);
        return CardMapper.toResponse(cardUpdated);
    }

    public void delete(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card não encontrado"));

        cardRepository.delete(card);
    }

}
