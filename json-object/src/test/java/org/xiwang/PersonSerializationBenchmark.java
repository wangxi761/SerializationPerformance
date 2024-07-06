package org.xiwang;

import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class PersonSerializationBenchmark {
	private Person person;
	private final int iterations = 100000;
	String personString;
	
	@Setup
	public void setup() {
		person = new Person();
		person.setName("John Doe");
		person.setAge(30);
		person.setHobbies(new ArrayList<>(Arrays.asList("reading", "swimming", "jogging")));
		
		personString = JsonUtil.toJacksonJson(person);
	}
	
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testJacksonSerialize() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.toJacksonJson(person);
		}
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testManualSerialize() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.toManualJson(person);
		}
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testJacksonDeserialize() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.fromJacksonJson(personString);
		}
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testManualDeserialize() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.fromManualJson(personString);
		}
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testManualDeserializeByOrgJson() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.fromManualJsonByOrgJson(personString);
		}
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void testManualSerializeByOrgJson() {
		for (int i = 0; i < iterations; i++) {
			JsonUtil.toManualJsonByOrgJson(person);
		}
	}
	
	
	
	@SneakyThrows
	public static void main(String[] args) {
		Options opt = new OptionsBuilder()
			.include(PersonSerializationBenchmark.class.getSimpleName())
			.forks(1)
			.warmupIterations(5)
			.measurementIterations(5)
			.build();
		new Runner(opt).run();
	}
}
