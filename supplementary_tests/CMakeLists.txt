cmake_minimum_required(VERSION 3.5.1)
project(supplementary_tests)

add_compile_options(-std=c++14)

find_package(catkin REQUIRED COMPONENTS 
    roscpp
    fsystem
    event_handling
    alica_engine
    alica_dummy_proxy
    alica_ros_proxy
    alica_simple_solver
    alica_common_config
    alica_solver_interface
    alica_test_utility
    constraintsolver
    autodiff
)

include_directories(include)

catkin_package(
  INCLUDE_DIRS include autogenerated/include
  LIBRARIES autogen_code_supplementary_tests 
  CATKIN_DEPENDS alica_engine alica_dummy_proxy alica_solver_interface alica_simple_solver constraintsolver
)

include_directories(include autogenerated/include ${catkin_INCLUDE_DIRS})
file(GLOB_RECURSE autogen_SOURCES "autogenerated/src/*.cpp")
add_library(autogen_code_supplementary_tests ${autogen_SOURCES};)
target_link_libraries(autogen_code_supplementary_tests ${catkin_LIBRARIES})

if (CATKIN_ENABLE_TESTING)

    find_package(rostest REQUIRED)
    #file(GLOB_RECURSE test_supplementary_SOURCES "src/test/*.cpp" src/test_supplementary.cpp)
    set(test_supplementary_SOURCES
        src/test_supplementary.cpp
        src/test/test_main.cpp
        src/test/test_alica_problem_composition.cpp
        src/test/test_alica_ros_proxy.cpp
        src/test/test_variable_handling.cpp
        src/test/test_alica_gsolver_plan.cpp
    )

    add_rostest_gtest(${PROJECT_NAME}-test test/supplementary_tests.test ${test_supplementary_SOURCES})
    target_link_libraries(${PROJECT_NAME}-test ${catkin_LIBRARIES} autogen_code_supplementary_tests )
   
endif()
