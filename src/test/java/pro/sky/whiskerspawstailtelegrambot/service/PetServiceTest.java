package pro.sky.whiskerspawstailtelegrambot.service;

import java.io.IOException;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.loger.FormLogInfo;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pro.sky.whiskerspawstailtelegrambot.repository.PetRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;
    @Mock
    PetMapper petMapper;
    @InjectMocks
    PetService out;
    PetMapper mapper = new PetMapperImpl();

    @Test
    void findPetPositiveTest() {
        Pet pet = getTestPet();
        lenient().when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        lenient().when(petMapper.toRecord(pet)).thenReturn(mapper.toRecord(pet));
        PetRecord exceptedRecord = mapper.toRecord(pet);
        PetRecord actual = out.findPet(1L);
        assertEquals(actual, exceptedRecord);
    }

    @Test
    void findPetNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.findPet(1L));
    }

    @Test
    void findAllPetEmptyListTest() {
        lenient().when(petRepository.findAll()).thenReturn(List.of());
        assertTrue(out.findAllPet().isEmpty());
    }
    @Test
    void findAllPetPositiveTest() {
        List<Pet> pets = getTestPetList();
        List<PetRecord> dogRecords = getTestPetRecords();

        lenient().when(petRepository.findAll()).thenReturn(pets);
        lenient().when(petMapper.toRecordList(pets)).thenReturn(mapper.toRecordList(pets));

        List<PetRecord> checkedDogRecords = List.copyOf(out.findAllPet());

        assertEquals(checkedDogRecords.size(), dogRecords.size());
        assertTrue(out.findAllPet().containsAll(dogRecords));
    }

    @Test
    void removePetPositiveTest() {
        Pet pet = getTestPet();

        lenient().when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        lenient().when(petMapper.toRecord(pet)).thenReturn(mapper.toRecord(pet));
        lenient().doNothing().when(petRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> out.removePet(1L));

    }

    @Test
    void removePetNegativeTest() {

        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.removePet(1L));

    }

    @Test
    void editPetPositiveTest() throws IOException {
        Pet pet = getTestPet();
        PetRecord petRecord = mapper.toRecord(pet);
        String age = petRecord.getAge();
       PetService petService = mock(PetService.class);
        doNothing().when(petService).editPet(petRecord.getId(), null, "1", null, null, null);
        Assertions.assertThatNoException().isThrownBy(() -> petService.editPet(pet.getId(), null, "1", null, null, null));
        verify(petService, times(1)).editPet(pet.getId(), null, "1", null, null, null);

    }

    @Test
    void editPetNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.editPet(1L, "", "1", "", "Cat", getTestPhoto()));
    }

    @Test
    void uploadPhotoNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrowsExactly(ElemNotFound.class, () -> out.uploadPhoto(1L, getTestPhoto()));
    }

    @Test
    void uploadPhotoPositiveTest() throws IOException {
        Pet pet = getTestPet();
        MultipartFile photo = getTestPhoto();
        pet.setPhoto(photo.getBytes());
        pet.setFilePath("/ptath");
        pet.setFileSize(photo.getSize());
        pet.setMediaType(photo.getContentType());
        Long id = pet.getId();
        PetService petService = mock(PetService.class);
        doNothing().when(petService).uploadPhoto(pet.getId(), photo);
        Assertions.assertThatNoException().isThrownBy(() ->petService.uploadPhoto(pet.getId(), photo));
        verify(petService, times(1)).uploadPhoto(id, photo);

    }

    @Test
    void addIdAdoptiveParentTest() {

        PetService petService = mock(PetService.class);
        doNothing().when(petRepository).addIdAdoptiveParent(1L, 1L);
        petRepository.addIdAdoptiveParent(1L, 1L);
        verify(petRepository, times(1)).addIdAdoptiveParent(1L, 1L);
    }


    /**
     * ?????? ?????? ??????????
     * @return
     */
    private Pet getTestPet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setFullName("?????????? ??????????????????");
        pet.setAge("3");
        pet.setDescription("????????????????????");
        pet.setFileSize(1024);
        pet.setShelter(getTestShelter());
        pet.setPhoto(null);
        pet.setMediaType("");
        pet.setReports(List.of());
        pet.setAdoptiveParent(null);
        return pet;
    }

    /**
     * ???????????? ?????? ??????????
     * @return
     */
    private Shelter getTestShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        return shelter;
    }

    /**
     * ?????????????????? ??????????
     * @return
     */
    private List<Pet> getTestPetList() {
        List<Pet> pets = new ArrayList<>();

        Pet pet = new Pet();
        pet.setId(2L);
        pet.setFullName("??????????");
        pet.setAge("5");
        pet.setDescription("????????????????????");
        pet.setFileSize(1024);
        pet.setShelter(getTestShelter());
        pet.setPhoto(null);
        pet.setMediaType("");
        pet.setReports(List.of());
        pet.setAdoptiveParent(null);

        pets.add(pet);
        pets.add(getTestPet());
        return pets;
    }

    /**
     * ???????????????????????????? ?????????????????? ?????????? ?? ??????????????
     * @return
     */

    private List<PetRecord> getTestPetRecords() {
        List<PetRecord> petRecords = new ArrayList<>();
        for (Pet pet : getTestPetList()) {
            petRecords.add(mapper.toRecord(pet));
        }
        return petRecords;
    }




    /**
     * ???????? ???????? ?????? ??????????
     * @return
     */

    private MockMultipartFile getTestPhoto() {
        return new MockMultipartFile("data", "photo.jpeg",
            MediaType.MULTIPART_FORM_DATA_VALUE, "photo.jpeg".getBytes());
    }

    @Test
    void getAllPetAdoptiveParentByChatId() throws Exception {
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setPetType("dog");

        Collection<Pet> pets = new ArrayList<>();
        pets.add(pet1);

        PetRecord petRecord1= new PetRecord();
        petRecord1.setId(1L);
        petRecord1.setPetType("dog");

        Collection<PetRecord> petRecords = new ArrayList<>();
        petRecords.add(petRecord1);

        when(petRepository.getAllByAdoptiveParent_ChatId(anyLong())).thenReturn(pets);
        when(petMapper.toRecordList(pets)).thenReturn(petRecords);

        assertThat(
            out.getAllPetAdoptiveParentByChatId(anyLong())).isEqualTo(
            petRecords);
        verify(petRepository, times(1)).getAllByAdoptiveParent_ChatId(anyLong());
    }
    @Test
    void getAllPetAdoptiveParentByChatIdNegative() {
        when(petRepository.getAllByAdoptiveParent_ChatId(anyLong())).thenReturn(null);

        assertThatExceptionOfType(Exception.class).isThrownBy(
            () -> out.getAllPetAdoptiveParentByChatId(anyLong()));
        verify(petRepository, times(1)).getAllByAdoptiveParent_ChatId(anyLong());
    }
}