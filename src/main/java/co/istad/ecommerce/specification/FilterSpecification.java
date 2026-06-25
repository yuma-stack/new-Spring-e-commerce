package co.istad.ecommerce.specification;

import co.istad.ecommerce.features.category.dto.RequestDto;
import co.istad.ecommerce.features.category.dto.SearchRequestDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterSpecification<T> {
    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtos, RequestDto.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SearchRequestDto requestDto : searchRequestDtos) {
                switch (requestDto.getOperation()){
                    case EQUAL :
                        Predicate equal = criteriaBuilder.equal(root.get(requestDto.getColumn()),
                                requestDto.getValue());
                        predicates.add(equal);
                        break;
                    case NOT_EQUAL :
                        Predicate notEqual = criteriaBuilder.notEqual(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(notEqual);
                        break;
                    case LIKE :
                        Predicate like = criteriaBuilder.like(root.get(requestDto.getColumn()),"%"+ requestDto.getValue()+"%");
                        predicates.add(like);
                        break;
                    case GREATER:
                        Predicate greater = criteriaBuilder.greaterThan(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(greater);
                        break;
                    case GREATER_OR_EQUAL:
                        Predicate greaterOrEqual =
                                criteriaBuilder.greaterThanOrEqualTo(root.get(requestDto.getColumn()),
                                        requestDto.getValue());
                        predicates.add(greaterOrEqual);
                        break;
                    case LESS:
                        Predicate less = criteriaBuilder.lessThan(root.get(requestDto.getColumn()),
                                requestDto.getValue());
                        predicates.add(less);
                        break;
                    case LESS_OR_EQUAL:
                        Predicate lessOrEqual =
                                criteriaBuilder.lessThanOrEqualTo(root.get(requestDto.getColumn()),
                                        requestDto.getValue());
                        predicates.add(lessOrEqual);
                        break;
                    case IS_NOT_NULL:
                        Predicate isNotNull = null;
                        criteriaBuilder.isNotNull(root.get(requestDto.getColumn()));
                        predicates.add(isNotNull);
                        break;
                    case IS_NULL:
                        Predicate isNull =
                                criteriaBuilder.isNull(root.get(requestDto.getColumn()));
                        predicates.add(isNull);
                        break;
                    case BETWEEN:
                        String[] split1 = requestDto.getValue().split(",");
                        Predicate between =
                                criteriaBuilder.between(root.get(requestDto.getColumn()),split1[0],split1[1]);
                        predicates.add(between);
                        break;
                    case JOIN:
                        Predicate join = criteriaBuilder.equal(root.join(requestDto.getJoinTable())
                                .get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(join);
                        break;
                    default:
                        throw new RuntimeException("Unexpected Value.");
                }
            }
            if (globalOperator == (RequestDto.GlobalOperator.AND)) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}

