### 11
# null 대신 Optional
## Optional 적용 패턴
- 빈 Optional
```java
Optional<Car> optCar = Optional.empty();
```

## null이 아닌 값으로 Optional 만들기
- 정적 팩토리 메서드 Optional.of로 null이 아닌 값을 포함하는 Optional 생성

```java
Optional<Car> optCar = Optional.of(car);
```
- 이제 car가 null이라면 즉시 NPE가 발생한다. of를 사용하지 않았다면 서비스를 실행 한 후 인지 했을 것

## null 값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.ofNullable(car);
```
- car가 null이면 빈 Optional 객체가 반환된다.
- Optional이 비어있으면 get을 호출했을때 예외가 발생한다. 즉 Optional을 잘못사용하면 null문제가 발생한다. 따라서 먼저 Optional로 명시적인 검사를 제거할 수 있는 방법을 살펴본다.

## 11.3.2
# 맵으로 Optional값을 추출하고 변환하기
- 보통 객체의 정보를 추출할 때는 Optional을 사용할 때가 많다.
```java
String name = null;
if(insurance != null){
    name = insurance.getName();
    //정보 접근전에 null을 체크하는 과정
}
```

- 이런 유형에 사용할 수 있도록 Optional은 map 메서드를 지원한다.

```java
Optional<insurance> optIns = Optional.ofNullable(insurance);
Optional<String> name = optIns.map(insurance::getName);
```

- 4,5장에서 살펴본 스트림의 map메서드와 개념적으로 비슷하다.
- 스트림의 map은 스트림의 각 요소에 제공된 함수를 적용하는 연산자이다.
- Optional 객체를 최대 요소가 개수하 한 개 이하인 데이터 컬렉션으로 생각 할 수 있다.
- Optional이 비어있으면 아무일도 일어나지 않는다.

## 11.3.3
# flatMap으로 Optional 객체 연결
```java
Optional<Person> optPerson = Optional.of(person);
Optional<String> name = optPerson.map(Person::getCar)
.map(Car::getInsurance)
.map(Insurance::getName);
//컴파일 안된다.
```
- 변수 OptPerson 형식은 Optional`<person>` 이므로 map메서드를 호출 할 수 있다. 하지만 getCar는 Optonal`<car>` 형식의 객체를 반환한다.
즉, map연산의 결과는 Optional`Optional<car>>` 형식의 객체다
- 중첩 Optional의 경우 스트림의 flatMap을 적용한다.
- 스트림의 flatMap은 함수를 인수로 받아서 다른 스트림을 반환하는 메서드다. 보통 인수로 받은 함수를 스트림의 각 요소에 적용하면 스트림의 스트림이 만들어진다.
하지만 flatMap은 인수로 받은 함수를 적용해서 생성된 각각의 스트림에서 콘텐츠만 남는다.
즉, 함수를 적용해서 생성된 모든 스트림의 하나의 스트림으로 병합된다.


- flatMap은 병합한다.
-  JS 기준으로 생각해보자
```javascript
let arr1 = [1, 2, 3, 4];

arr1.map(x => [x * 2]);
// [[2], [4], [6], [8]]

arr1.flatMap(x => [x * 2]);
// [2, 4, 6, 8]

// 한 레벨만 평탄화됨
arr1.flatMap(x => [[x * 2]]);
// [[2], [4], [6], [8]]

```

```java
//철자를 모두 분리하는 예제
animal = ["cat","dog"] 

//원하는 결과  = [ "c", "a", "t", "d", "o", "g" ]

List<String[]> results = animals.stream().map(animal -> animal.split(""))
                                .collect(Collectors.toList());
//map 사용시 변환타임은 List<String[]>   [  [  "c", "a", "t"  ] , [ "d", "o", "g" ]  ]                         

```

- flatMap은 중복된 스트림을 1차원으로 평면화 시키는 것이 핵심이다.
- 책에서는 너무 복잡하여 처음보면 이해가 잘 안된다.

```java
List<String> results = animals.stream().map(animal -> animal.split(""))
        .flatMap(Arrays::stream)
        .collect(Collectors.toList());
//results = [c, a, t, d, o, g]

```

## 11.3.4
# Optional 스트림 조작
- 보통 스트림 요소를 조작하려면 변환, 필터 등의 일련의 여러 긴 체인이 필요한데
이 예제는 Optional로 값이 감싸있으므로 이 과정이 더 복잡해졌다.

```java
persons.stream()
.map(Person::getCar) 
//사람 목록을 각 사람이 보유한 자동차의 Optional<Car> 스트림으로 변환
.map(optCar -> optCar.flatMap(Car::getInsurance)) 
//FlatMap연산을 이용해 Optional<Car>을 해당 Optional<Insurance>로 변환
.map(optIns -> optIns.flatMap(Insurance::getName)) 
// Optional<Insurance>를 해당 이름의 Optional<String>으로 맵핑
.flatMap(Optional::stream) 
// Stram<Option<String>>을 현재 이름을 포함하는 Stream<String>으로 변환
.collect(toSet()) 
// 결과 문자열을 중복되지 않은 값을 갖도록 집합으로 수집
```


- 세번의 변환과정을 거친 결과 `Stram<Option<String>>` 을 얻는데 사람이 차를 갖고 있지 않거나 또는 차가 보험에 가입되어 있지 않아 결과가 비어 있을 수 있다.
Option 덕분에 이런 종류의 연산을 Null걱정없이 안전하게 처리 할 수 있지만 마지막 결과를 얻으려면 빈 Optional을 제거하고 값을 언랩해야 한다는 것이 문제다.

- 다음 코드 처럼 filter, map을 순서적으로 이용해 결과를 얻을 수 있다.
```java
Stream<Optional<String>> stream = ...
Set<String> result = stream.filter(Optional::isPresent)
.map(Optional::get)
.collect(toSet())
```

- Optional 클래스의 stream() 메서드를 이용하면 한번의 연산으로 같은 결과를 얻을 수 있다.
- 이 메서드는 각 Optional이 비어있는지 아닌지에 따라 Optional을 0개 이상의 항목을 포함하는 스트림으로 변환한다.

## 11.3.5
# 디폴트 액션과 Optinal 언랩
- get()은 값을 읽는 가장 간단한 메서드면서 동시에 가장 안전하지 않은 메서드이다.
값이 있으면 값을 주고 없으면 NoSuchElementException을 발생시킨다.
    - Optional에 값이 반드시 있다고 가정할 수 있는 상황이 아니면 get메서드를 쓰지 않는 것이 바람직하다.
    
- orElse(T other) 메서드를 이용하면 Optional에 값이 포함하지 않을 때 기본 값을 제공한다.
- orElseGet(Supplier`<? extends T>` other)는 orElse 메서드에 대응하는 게으른 버전의 메서드이다. Optional에 값이 없을 때만 Supplier가 실행된다.
    - 디폴트 메서드를 만드는데 시간이 걸리거나 Optional이 비어있을 때만 기본값을 생성하고 싶다면(반드시 기본값이 필요시) orElseGet를 사용해야한다.

- orElseThrow는 Optional이 비어있을때 예외를 발생시킨다. 예외의 종류를 선택 할 수 있다.
- ifPresent를 이용하면 값이 존재할 때 인수로 넘겨준 동작을 실행할 수 있다. 값이 없으면 아무일도 일어나지 않는다.
- ifPresentOfElse 메서드는  Optional이 비어있을 때 실행할 수 있는 Runnable을 인수로 받는다는 점만 ifPresent와 다르다.

## 1.1.36 
# 두 Optional 합치기

- Optional 언랩하지 않고 두 Optional 합치기

```java

public Optional<Ins> nullSafeFindCheapestIns(Optional<Person> person, Optional<Car> car){
    return person.flatMap(p -> car.map(c->findCheapestIns(p,c)));
}

```
- 첫번째 Optional에 flatMap을 호출했으므로 첫번째 Optional이 비어있다면 인수로 전달한 람다가 실행되지 않고 그대로 빈 Optional 반환한다.
- Person가 존재할때는 function의 입력으로 person을 사용한다.
- 바디에서는 두번째 Optional에 map을 호출하므로 Optional이 Car값을 포함하지 않으면 Function은 빈 Optional을 반환하므로 결국 nullSafeFindCheapestIns는 빈 Optional을 반환하게 된다.


## 1.1.37
# 필터로 특정값 거르기

```java
Optional<Ins> optIns = ...
optIns.filter(ins -> "CamIns".equlas(ins.getName()))
.ifPresent(x-> sout("OK"))
```
- filter메서드는 프레디케이트를 인수로 받는다. Optional 객체가 값을 가지며 프레디케이드와 일치하면 filter 메서드는 그 값을 반환하고 그렇지 않으면 빈 Optional 객체를 반환한다.

- Optional 클래스 메서드 정리 : 383P

## 1.1.4 
# Optional 실용 예제
- 기존 자바 API는 Optional을 적절하게 활용하지 못하고 있다.
- 작은 유틸리티 메서드를 추가하는 방식으로 이 문제를 해결 할 수 있다.

## 1.1.41
# 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
- Map의 get메서드는 요청한 키에 대응하는 값을 찾지 못하면 null을 반환한다. (map.get("test"))
- get메서드의 반환값은 Optional로 감쌀 수 있다.
```java

Object value = map.get("key");
```
- map에서 반환하는 값을 Optional로 감싼다.
```java
Optional<Object> value = Optional.ofNullable(map.get("key"));
```

## 1.1.42
# 예외와 Optional 클래스
- null을 발생시키지 않고 예외를 발생시키는 경우 : Integer.parseInt는 정수로 바꾸지 못할 때 NumberFormatException을 반환한다.

- 정수로 반환 할 수 없는 문자열 문제를 빈 Optional로 해결 할 수 있다.

```java
try{
    return Optional.of(Integer.parseInt(s))l
}catch(NumberFormatException e){
    return Optional.empty();
}
```
- 기존의 거추장스러운 try/catch로직을 사용 할 필요없음

## 1.1.43 기본형 Optional을 사용하지 말아야하는 이유
- Optional의 최대 요소 수는 한 개이므로 Optional 에서는 기본형 특화 클래스로 성능을 개선 할 수 없다.
- OptionalInt , OptionalLong 등.
- 기본형 특화 Optional은 map, flatmap,filter 등을 지원하지 않는다.
