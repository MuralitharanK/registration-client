package io.mosip.kernel.masterdata.test.service;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.MappingException;
import org.springframework.dao.DataRetrievalFailureException;

import io.mosip.kernel.masterdata.dto.MachineDetailDto;
import io.mosip.kernel.masterdata.dto.MachineDetailResponseDto;
import io.mosip.kernel.masterdata.dto.MachineDetailResponseIdDto;
import io.mosip.kernel.masterdata.entity.MachineDetail;
import io.mosip.kernel.masterdata.exception.MachineDetailFetchException;
import io.mosip.kernel.masterdata.exception.MachineDetailMappingException;
import io.mosip.kernel.masterdata.exception.MachineDetailNotFoundException;
import io.mosip.kernel.masterdata.repository.MachineDetailRepository;
import io.mosip.kernel.masterdata.service.impl.MachineDetailServiceImpl;
import io.mosip.kernel.masterdata.utils.ObjectMapperUtil;

@RunWith(MockitoJUnitRunner.class)
public class MachineDetailServiceImplTest {

	@InjectMocks
	private MachineDetailServiceImpl machineDetailServiceImpl;

	@Mock
	private MachineDetailRepository machineDetailsRepository;

	@Mock
	private ObjectMapperUtil objectMapperUtil;

	@Before
	public void setUp() {
		machineDetailServiceImpl = new MachineDetailServiceImpl();
		MockitoAnnotations.initMocks(this);
	}

	public MachineDetail machineDetail = new MachineDetail();
	public List<MachineDetail> machineDetailList = new ArrayList<>();

	@Test
	public void testGetMachineDetailIdLang() {
		MachineDetailDto machineDetailDto = new MachineDetailDto();
		machineDetailDto.setId("1000");
		machineDetailDto.setName("HP");
		machineDetailDto.setSerialNum("1234567890");
		machineDetailDto.setMacAddress("100.100.100.80");
		machineDetailDto.setLangCode("ENG");
		machineDetailDto.setIsActive(true);

		MachineDetail machineDetail = new MachineDetail();
		machineDetail.setId("1000");
		machineDetail.setName("HP");
		machineDetail.setSerialNum("1234567890");
		machineDetail.setMacAddress("100.100.100.80");
		machineDetail.setLangCode("ENG");
		machineDetail.setIsActive(true);
		Mockito.when(machineDetailsRepository.findAllByIdAndLangCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(machineDetail);
		Mockito.when(objectMapperUtil.map(machineDetail, MachineDetailDto.class)).thenReturn(machineDetailDto);
		MachineDetailResponseIdDto actual = machineDetailServiceImpl.getMachineDetailIdLang(Mockito.anyString(),
				Mockito.anyString());
		Assert.assertNotNull(actual);
		Assert.assertEquals(machineDetailDto.getId(), actual.getMachineDetail().getId());

	}

	@Test(expected = MachineDetailNotFoundException.class)
	public void testGetMachineDetailIdLangThrowsMachineNotFoundExcetion() {
		Mockito.when(machineDetailsRepository.findAllByIdAndLangCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(null);
		machineDetailServiceImpl.getMachineDetailIdLang("1000", "ENG");

	}

	@Test(expected = MachineDetailFetchException.class)
	public void testGetMachineDetailIdLangThrowsDataAccessExcetion() {
		Mockito.when(machineDetailsRepository.findAllByIdAndLangCode(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(DataRetrievalFailureException.class);
		machineDetailServiceImpl.getMachineDetailIdLang("1000", "ENG");

	}

	@Test(expected = MachineDetailMappingException.class)
	public void testGetMachineDetailIdLangThrowsIllegalArgumentExcetion() {
		Mockito.when(machineDetailsRepository.findAllByIdAndLangCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(machineDetail);
		Mockito.when(objectMapperUtil.map(machineDetail, MachineDetailDto.class))
				.thenThrow(IllegalArgumentException.class);
		machineDetailServiceImpl.getMachineDetailIdLang("1000", "ENG");

	}

	@Test(expected = MachineDetailMappingException.class)
	public void testGetMachineDetailIdLangThrowsMappingExcetion() {
		Mockito.when(machineDetailsRepository.findAllByIdAndLangCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(machineDetail);
		Mockito.when(objectMapperUtil.map(machineDetail, MachineDetailDto.class)).thenThrow(MappingException.class);
		machineDetailServiceImpl.getMachineDetailIdLang("1000", "ENG");

	}

	@Test
	public void testGetMachineDetailAll() {
		List<MachineDetailDto> machineDetailDtoList = new ArrayList<MachineDetailDto>();
		MachineDetailDto machineDetailDto = new MachineDetailDto();
		machineDetailDto.setId("1000");
		machineDetailDto.setName("HP");
		machineDetailDto.setSerialNum("1234567890");
		machineDetailDto.setMacAddress("100.100.100.80");
		machineDetailDto.setLangCode("ENG");
		machineDetailDto.setIsActive(true);
		machineDetailDtoList.add(machineDetailDto);
		
		MachineDetail machineDetail = new MachineDetail();
		machineDetail.setId("1000");
		machineDetail.setName("HP");
		machineDetail.setSerialNum("1234567890");
		machineDetail.setMacAddress("100.100.100.80");
		machineDetail.setLangCode("ENG");
		machineDetail.setIsActive(true);

		List<MachineDetail> machineDetailList = new ArrayList<MachineDetail>();
		machineDetailList.add(machineDetail);
		Mockito.when(machineDetailsRepository.findAll()).thenReturn(machineDetailList);
		Mockito.when(objectMapperUtil.mapAll(machineDetailList, MachineDetailDto.class))
				.thenReturn(machineDetailDtoList);
		MachineDetailResponseDto actual = machineDetailServiceImpl.getMachineDetailAll();

		Assert.assertNotNull(actual);
		Assert.assertTrue(actual.getMachineDetails().size() > 0);

	}

	@Test(expected = MachineDetailNotFoundException.class)
	public void testGetMachineDetailAllThrowsMachineNotFoundExcetion() {
		Mockito.when(machineDetailsRepository.findAll()).thenThrow(MachineDetailNotFoundException.class);
		machineDetailServiceImpl.getMachineDetailAll();

	}

	@Test(expected = MachineDetailFetchException.class)
	public void testGetMachineDetailAllThrowsDataAccessExcetion() {
		Mockito.when(machineDetailsRepository.findAll()).thenThrow(DataRetrievalFailureException.class);
		machineDetailServiceImpl.getMachineDetailAll();

	}

	@Test(expected = MachineDetailMappingException.class)
	public void testGetMachineDetailAllThrowsIllegalArgumentExcetion() {
		MachineDetail machineDetail = new MachineDetail();
		machineDetail.setId("1000");
		machineDetail.setName("HP");
		machineDetail.setSerialNum("1234567890");
		machineDetail.setMacAddress("100.100.100.80");
		machineDetail.setLangCode("ENG");
		machineDetail.setIsActive(true);

		List<MachineDetail> machineDetailList = new ArrayList<MachineDetail>();
		machineDetailList.add(machineDetail);
		Mockito.when(machineDetailsRepository.findAll()).thenReturn(machineDetailList);
		Mockito.when(objectMapperUtil.mapAll(machineDetailList, MachineDetailDto.class))
				.thenThrow(IllegalArgumentException.class);
		machineDetailServiceImpl.getMachineDetailAll();

	}

	@Test(expected = MachineDetailMappingException.class)
	public void testGetMachineDetailAllThrowsMappingExcetion() {
		MachineDetail machineDetail = new MachineDetail();
		machineDetail.setId("1000");
		machineDetail.setName("HP");
		machineDetail.setSerialNum("1234567890");
		machineDetail.setMacAddress("100.100.100.80");
		machineDetail.setLangCode("ENG");
		machineDetail.setIsActive(true);

		List<MachineDetail> machineDetailList = new ArrayList<MachineDetail>();
		machineDetailList.add(machineDetail);
		Mockito.when(machineDetailsRepository.findAll()).thenReturn(machineDetailList);
		Mockito.when(objectMapperUtil.mapAll(machineDetailList, MachineDetailDto.class))
				.thenThrow(MappingException.class);
		machineDetailServiceImpl.getMachineDetailAll();

	}

	@Test(expected = MachineDetailNotFoundException.class)
	public void testGetMachineDetailAllThrowsMachineDetailNotFoundException() {
		doReturn(null).when(machineDetailsRepository).findAll();
		machineDetailServiceImpl.getMachineDetailAll();

	}

}
